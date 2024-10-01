package com.example.unit.testing.junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBookRecords(){
        return bookRepository.findAll();
    }

    @GetMapping("/{bookId}")
    public Optional<Book> getBookById(@PathVariable Long bookId){
        return bookRepository.findById(bookId);
    }

    @PostMapping
    public Book createBookRecord(@RequestBody @Validated Book bookRecord){
        return  bookRepository.save(bookRecord);
    }

    @PutMapping
    public Book updateBook(@RequestBody @Validated Book bookRecord){
        if(bookRecord==null || bookRecord.getBookId()==null){
            throw new RuntimeException("book record or Id must be not null !");
        }
        Optional<Book> optionalBook=bookRepository.findById(Long.valueOf(bookRecord.getBookId()));
        if(!optionalBook.isPresent()){
            throw new RuntimeException("Book with Id : "+bookRecord.getBookId()+ " not exist");
        }

        Book existingBookRecord=optionalBook.get();
        existingBookRecord.setName(bookRecord.getName());
        existingBookRecord.setSummary(bookRecord.getSummary());
        existingBookRecord.setRating(bookRecord.getRating());

        return bookRepository.save(existingBookRecord);
    }

    // we will create a delete Api and test it using TDD approach

    @DeleteMapping("/{bookId}")
    public void deleteBookById(@PathVariable Long bookId) throws Exception{

        if(!bookRepository.findById(bookId).isPresent()){
            throw new RuntimeException("bookId : "+bookId+" not present");
        }
        bookRepository.deleteById(bookId);
    }


}
