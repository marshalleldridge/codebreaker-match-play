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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    name = "user_profile"
)
@SuppressWarnings("JpaDataSourceORMInspection")
public class User {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @NonNull
  @Column(
      name = "user_id",
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

  @NonNull
  @Column(nullable = false, updatable = false, unique = true)
  private String oauthKey;

  @NonNull
  @Column(nullable = false, unique = true)
  private String displayName;

  @Column(nullable = false)
  private boolean inactive;

  @NonNull
  @OrderBy("created DESC")
  @OneToMany(mappedBy = "originator", fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH
      })
  private final List<Match> matchesOriginated = new LinkedList<>();

  @NonNull
  @OrderBy("created DESC")
  @JoinTable(name = "user_match_participation",
      joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "match_id", nullable = false, updatable = false)}
  )
  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH
      })
  private final List<Match> matchesParticipating = new LinkedList<>();

  @NonNull
  @OrderBy("created DESC")
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private final List<Code> codes = new LinkedList<>();

  //TODO Consider weather adding the one to many for guesses makes sense here.


  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  public boolean isInactive() {
    return inactive;
  }

  public void setInactive(boolean inactive) {
    this.inactive = inactive;
  }

  @NonNull
  public List<Match> getMatchesOriginated() {
    return matchesOriginated;
  }

  @NonNull
  public List<Match> getMatchesParticipating() {
    return matchesParticipating;
  }

  @NonNull
  public List<Code> getCodes() {
    return codes;
  }
}
