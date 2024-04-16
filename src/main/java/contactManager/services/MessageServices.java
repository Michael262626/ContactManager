package contactManager.services;

import contactManager.data.model.Message;
import contactManager.dtos.request.DeleteMessageRequest;
import contactManager.dtos.request.GetMessageRequest;
import contactManager.dtos.request.MessageRequest;
import contactManager.dtos.request.ReceiveMessageRequest;
import contactManager.dtos.response.DeleteMessageResponse;
import contactManager.dtos.response.GetMessageResponse;
import contactManager.dtos.response.MessageResponse;

import java.util.List;

public interface MessageServices {
    Message sendMessage(MessageRequest messageRequest);
    MessageResponse receiveMessage(ReceiveMessageRequest receiveMessageRequest);
    List<Message> getConversation();
    DeleteMessageResponse deleteMessage(DeleteMessageRequest deleteMessageRequest, Message message);
    Message searchMessages(GetMessageRequest getMessageRequest, Message message);
}
