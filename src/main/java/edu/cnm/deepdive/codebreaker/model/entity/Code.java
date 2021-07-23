package edu.cnm.deepdive.codebreaker.model.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
@SuppressWarnings("JpaDataSourceORMInspection")
public class Code {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @NonNull
  @Column(
      name = "code_id",
      updatable = false,
      nullable = false,
      columnDefinition = "CHAR(16) FOR BIT DATA"
  )
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @Column(nullable = false, updatable = false)
  private int length;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String pool;

  @NonNull
  @Column(name = "code_text", nullable = false, updatable = false)
  private String text;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "match_id", nullable = true, updatable = false)
  private Match match;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "user_id", nullable = true, updatable = false)
  private User user;

  @NonNull
  @OrderBy("created ASC")
  @OneToMany(mappedBy = "code", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  private final List<Guess> guesses = new LinkedList<>();


  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  @NonNull
  public String getPool() {
    return pool;
  }

  public void setPool(@NonNull String pool) {
    this.pool = pool;
  }

  @NonNull
  public String getText() {
    return text;
  }

  public void setText(@NonNull String text) {
    this.text = text;
  }

  public Match getMatch() {
    return match;
  }

  public void setMatch(Match match) {
    this.match = match;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @NonNull
  public List<Guess> getGuesses() {
    return guesses;
  }
}
