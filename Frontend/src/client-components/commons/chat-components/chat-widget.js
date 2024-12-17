import React, { useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";
import { Modal, ModalHeader, ModalBody, ModalFooter, Button, Input } from "reactstrap";
import "../../../commons/styles/chat-style.css"; 

const UserChatModal = ({ userId }) => {
  const [client, setClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [typingUser, setTypingUser] = useState(null);
  const [isTyping, setIsTyping] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    if (!isOpen) return;

    const stompClient = new Client({
      brokerURL: "ws://localhost:8089/ws",
      reconnectDelay: 5000,
      onConnect: () => {
        console.log("Connected to WebSocket as client");

        stompClient.subscribe(`/user/${userId}/queue/messages`, (message) => {
          const msg = JSON.parse(message.body);
          setMessages((prev) => [...prev, msg]);
        });

        stompClient.subscribe(`/user/${userId}/queue/typing`, (typingMessage) => {
          const typingData = JSON.parse(typingMessage.body);
          setTypingUser(typingData.senderId);
          setTimeout(() => setTypingUser(null), 3000);
        });
      },
    });

    stompClient.activate();
    setClient(stompClient);

    return () => {
      if (stompClient) stompClient.deactivate();
    };
  }, [isOpen, userId]);

  const handleSendMessage = (e) => {
    e.preventDefault();
    if (!newMessage.trim() || !client) return;

    const message = {
      senderId: userId,
      receiverId: "admin",
      content: newMessage.trim(),
    };

    client.publish({
      destination: "/app/privateMessage",
      body: JSON.stringify(message),
    });

    setMessages((prev) => [...prev, message]);
    setNewMessage("");
    setIsTyping(false);
  };

  const handleTyping = () => {
    if (!client || isTyping) return;

    const typingNotification = {
      senderId: userId,
      receiverId: "admin",
      content: "is typing...",
    };

    client.publish({
      destination: "/app/typing",
      body: JSON.stringify(typingNotification),
    });

    setIsTyping(true);
    setTimeout(() => setIsTyping(false), 2000);
  };

  return (
    <div>
      <Button color="primary" onClick={() => setIsOpen(true)}>
        Open Chat with Admin
      </Button>

      <Modal isOpen={isOpen} toggle={() => setIsOpen(false)} size="lg">
        <ModalHeader toggle={() => setIsOpen(false)}>Chat with Admin</ModalHeader>
        <ModalBody>
          <div className="message-list">
            {messages.map((msg, idx) => (
              <div
                key={idx}
                className={`message ${msg.senderId === userId ? "sent" : "received"}`}
              >
                <p className="message-content">{msg.content}</p>
              </div>
            ))}
            {typingUser && (
              <div className="typing-indicator">{typingUser} is typing...</div>
            )}
          </div>
        </ModalBody>
        <ModalFooter>
          <form className="chat-form" onSubmit={handleSendMessage}>
            <Input
              type="text"
              value={newMessage}
              onChange={(e) => setNewMessage(e.target.value)}
              onKeyDown={handleTyping}
              placeholder="Type a message..."
            />
            <Button color="success" type="submit">
              Send
            </Button>
          </form>
        </ModalFooter>
      </Modal>
    </div>
  );
};

export default UserChatModal;
