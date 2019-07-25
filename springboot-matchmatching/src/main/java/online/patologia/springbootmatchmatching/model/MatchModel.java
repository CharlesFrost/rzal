package online.patologia.springbootmatchmatching.model;


import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class MatchModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String place;
    private LocalDate date;
    private int numberOfPlayers;

    public MatchModel(String place, LocalDate date, int numberOfPlayers) {
        this.place = place;
        this.date = date;
        this.numberOfPlayers = numberOfPlayers;
    }


    public MatchModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


}
