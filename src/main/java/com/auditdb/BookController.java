package com.auditdb;

import lombok.RequiredArgsConstructor;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.history.Revision;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class BookController {

    private final BookRepository repository;

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        Book saved = repository.save(book);
        Revision<Integer, Book> revision = repository.findLastChangeRevision(saved.getId()).get();

        System.out.println("=======================================================================================");
        System.out.println(revision);
        System.out.println("=======================================================================================");
        return saved;
    }

    @PutMapping("/{id}/{pages}")
    public Book updateBook(@PathVariable Long id, @PathVariable Integer pages) {
        Book book = repository.findById(id).get();
        book.setPages(pages);

        return repository.save(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
        return "book deleted";
    }


    @GetMapping("/getInfo/{id}")
    public String getInfo(@PathVariable Long id) {
        System.out.println("=======================================================================================");
        System.out.println(repository.findLastChangeRevision(id));
        System.out.println(repository.findLastChangeRevision(id).get().getEntity());
        System.out.println(repository.findLastChangeRevision(id).get().getMetadata());
        System.out.println("=======================================================================================");

        return repository.findLastChangeRevision(id).get().toString();
    }
}
