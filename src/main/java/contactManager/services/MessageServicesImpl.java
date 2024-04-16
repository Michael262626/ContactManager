package contactManager.services;

import contactManager.data.model.Message;
import contactManager.data.repository.Messages;
import contactManager.dtos.request.DeleteMessageRequest;
import contactManager.dtos.request.GetMessageRequest;
import contactManager.dtos.request.MessageRequest;
import contactManager.dtos.request.ReceiveMessageRequest;
import contactManager.dtos.response.DeleteMessageResponse;
import contactManager.dtos.response.MessageResponse;
import contactManager.exceptions.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static contactManager.utils.Mappers.*;

@Service
public class MessageServicesImpl implements MessageServices{
    @Autowired
    private Messages messages;
    @Override
    public Message sendMessage(MessageRequest messageRequest) {
        Message message = map(messageRequest);
        return messages.save(message);
    }

    @Override
    public MessageResponse receiveMessage(ReceiveMessageRequest receiveMessageRequest) {
        Message message = map(receiveMessageRequest);
        return mapMessageResponse(message);
    }

    @Override
    public List<Message> getConversation() {
        if(messages.findAll().isEmpty()) throw new MessageNotFoundException("No messages found");
        return messages.findAll();
    }
    @Override
    public DeleteMessageResponse deleteMessage(DeleteMessageRequest deleteMessageRequest, Message message) {
        messages.delete(message);
        return mapDeleteMessage(message);
    }
    @Override
    public Message searchMessages(GetMessageRequest getMessageRequest, Message message) {
        return messages.findMessageByMessageId(message.getSender());
    }
}
