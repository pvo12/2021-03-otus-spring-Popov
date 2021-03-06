package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_comments")
@NamedEntityGraph(name = "books-entity-graph",
        attributeNodes = {@NamedAttributeNode("book")})
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookBrief book;

    @Column(name = "comment", nullable = false, unique = false)
    private String comment;
}
