package fr.yachan.entity;

public class SumUpEntity {
    private int id;
    private int animeId;
    private String text;
    private String status;
    private int doneAt;

    public SumUpEntity(int id, int animeId, String text, String status, int doneAt) {
        this.id = id;
        this.animeId = animeId;
        this.text = text;
        this.status = status;
        this.doneAt = doneAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(int doneAt) {
        this.doneAt = doneAt;
    }
}
