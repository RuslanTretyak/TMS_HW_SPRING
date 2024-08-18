package application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Grooup grooup;
    @OneToOne
    @JoinColumn(name = "record_book_id", referencedColumnName = "id")
    private RecordBook recordBook;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Grooup getGrooup() {
        return grooup;
    }

    public void setGrooup(Grooup groop) {
        this.grooup = groop;
    }

    public RecordBook getRecordBook() {
        return recordBook;
    }

    public void setRecordBook(RecordBook recordBook) {
        this.recordBook = recordBook;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", grooup=" + grooup +
                ", recordBook=" + recordBook +
                '}';
    }
}
