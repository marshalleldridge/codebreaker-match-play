package edu.cnm.deepdive.codebreaker.model.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
@Table(
    name = "codebreaker_match",
    indexes = {
        @Index(columnList = "codeLength, pool"),
        @Index(columnList = "codesToGenerate"),
        @Index(columnList = "ending")
    }
)
@SuppressWarnings("unused")
public class Match {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @NonNull
  @Column(
      name = "match_id",
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
  private int codesToGenerate;

  @Column(nullable = false, updatable = false)
  private int codeLength;

  @NonNull
  @Column(nullable = false, updatable = false)
  private String pool;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date ending;

  @NonNull
  @Enumerated
  @Column(nullable = false, updatable = false)
  private Criterion criterion;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "originator_id", nullable = false, updatable = false)
  private User originator;

  @NonNull
  @OrderBy("displayName ASC")
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "matchesParticipating")
  private final List<User> participants = new LinkedList<>();

  @NonNull
  @OrderBy("created ASC")
  @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
  private final List<Code> codes = new LinkedList<>();

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public int getCodesToGenerate() {
    return codesToGenerate;
  }

  public void setCodesToGenerate(int codesToGenerate) {
    this.codesToGenerate = codesToGenerate;
  }

  public int getCodeLength() {
    return codeLength;
  }

  public void setCodeLength(int codeLength) {
    this.codeLength = codeLength;
  }

  @NonNull
  public String getPool() {
    return pool;
  }

  public void setPool(@NonNull String pool) {
    this.pool = pool;
  }

  @NonNull
  public Date getEnding() {
    return ending;
  }

  public void setEnding(@NonNull Date ending) {
    this.ending = ending;
  }

  @NonNull
  public Criterion getCriterion() {
    return criterion;
  }

  public void setCriterion(@NonNull Criterion criterion) {
    this.criterion = criterion;
  }

  @NonNull
  public User getOriginator() {
    return originator;
  }

  public void setOriginator(@NonNull User originator) {
    this.originator = originator;
  }

  @NonNull
  public List<User> getParticipants() {
    return participants;
  }

  @NonNull
  public List<Code> getCodes() {
    return codes;
  }

  public enum Criterion {
    ATTEMPTS, TIME
  }

}
