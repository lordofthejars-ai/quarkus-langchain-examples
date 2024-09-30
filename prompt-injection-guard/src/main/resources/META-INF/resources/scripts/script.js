// Handle sending messages when 'Send' button is clicked or 'Enter' key is pressed
function sendMessage() {
    const userInput = document.getElementById("user-input").value.trim();
    if (!userInput) return; // Don't send empty messages

    // Append user's message to the chat window
    appendMessage(userInput, "user-message");

    // Clear input field
    document.getElementById("user-input").value = "";

    // Scroll to the bottom of the chat window
    scrollToBottom();

    // Send the message via POST request to the /chat endpoint
    fetch('/chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ message: userInput })
    })
    .then(response => response.json())
    .then(data => {
        if (data.reply) {
            appendMessage(data.reply, "bot-message");
        } else {
            appendMessage("Error: No response from the server.", "bot-message");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        appendMessage("Error: Failed to fetch response.", "bot-message");
    });
}

// Function to append a new message to the chat window
function appendMessage(content, className) {
    const chatWindow = document.getElementById("chat-window");

    const messageElement = document.createElement("div");
    messageElement.classList.add("message", className);
    messageElement.textContent = content;

    chatWindow.appendChild(messageElement);
    scrollToBottom();
}

// Function to scroll the chat window to the bottom
function scrollToBottom() {
    const chatWindow = document.getElementById("chat-window");
    chatWindow.scrollTop = chatWindow.scrollHeight;
}

// Function to insert default text into the input field
function insertDefaultText(text) {
    const userInput = document.getElementById("user-input");
    userInput.value = text;
    userInput.focus();
}

// Send message when pressing Enter key
function handleEnter(event) {
    if (event.key === "Enter") {
        sendMessage();
    }
}
