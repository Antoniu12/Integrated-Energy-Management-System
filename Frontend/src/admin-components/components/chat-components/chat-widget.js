import React, { useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";
import Modal from "react-modal";
import "../../../commons/styles/chat-style.css";

Modal.setAppElement("#root");

const AdminChatModal = ({ selectedUser, isOpen, onClose }) => {
  const [client, setClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [typingUser, setTypingUser] = useState(null);

  useEffect(() => {
    if (!selectedUser || !isOpen) return;

    const stompClient = new Client({
      brokerURL: "ws://localhost:8089/ws",
      reconnectDelay: 5000,
      onConnect: () => {
        console.log(`Connected to WebSocket as admin`);

        stompClient.subscribe(`/user/admin/queue/messages`, (message) => {
          const msg = JSON.parse(message.body);
          setMessages((prev) => [...prev, msg]);
        });

        stompClient.subscribe(`/user/admin/queue/typing`, (typingMessage) => {
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
  }, [selectedUser, isOpen]);

  const handleSendMessage = () => {
    if (!client || !newMessage.trim()) return;

    const message = {
      senderId: "admin",
      receiverId: selectedUser,
      content: newMessage.trim(),
    };

    client.publish({
      destination: "/app/privateMessage",
      body: JSON.stringify(message),
    });

    setMessages((prev) => [...prev, message]);
    setNewMessage("");
  };

  const handleTyping = () => {
    if (!client) return;

    const typingNotification = {
      senderId: "admin",
      receiverId: selectedUser,
    };

    client.publish({
      destination: "/app/typing",
      body: JSON.stringify(typingNotification),
    });
  };

  return (
    <Modal isOpen={isOpen} onRequestClose={onClose} className="admin-chat-modal" overlayClassName="modal-overlay">
      <div className="modal-header">
        <h3>Chat with {selectedUser}</h3>
        <button onClick={onClose} className="close-button">&times;</button>
      </div>
      <div className="modal-content">
        <div className="message-list">
          {messages.map((msg, idx) => (
            <div
              key={idx}
              className={`message ${msg.senderId === "admin" ? "sent" : "received"}`}
            >
              <p>{msg.content}</p>
            </div>
          ))}
          {typingUser && <p className="typing-indicator">{typingUser} is typing...</p>}
        </div>
        <div className="message-input">
          <input
            type="text"
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            onKeyPress={handleTyping}
            placeholder="Type a message..."
          />
          <button onClick={handleSendMessage}>Send</button>
        </div>
      </div>
    </Modal>
  );
};

export default AdminChatModal;
