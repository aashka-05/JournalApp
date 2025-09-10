package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.JournalContent;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {
    @Autowired
    JournalEntryRepository journalRepo;

    public List<JournalContent> viewAll(){

        List<JournalContent> li = journalRepo.findAll();
        return li;
    }

    public Optional<JournalContent> viewbyId(ObjectId id){
        return journalRepo.findById(id);
    }

    public JournalContent add(JournalContent data){
        return journalRepo.save(data);
    }

    public void del(ObjectId id){
        journalRepo.deleteById(id);
    }
}
