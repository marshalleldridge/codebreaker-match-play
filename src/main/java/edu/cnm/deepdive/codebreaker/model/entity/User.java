package edu.cnm.deepdive.codebreaker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Encoder;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
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

  private static final Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

  @Id
  @JsonIgnore
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
  @Column(name = "rest_key", unique = true)
  @JsonProperty(value = "id", access = Access.READ_ONLY)
  private String key;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @JsonIgnore
  @Column(nullable = false, updatable = false, unique = true)
  private String oauthKey;

  @NonNull
  @Column(nullable = false, unique = true)
  private String displayName;

  @Column(nullable = false)
  private boolean inactive;

  @NonNull
  @JsonIgnore
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = true)
  private Date connected;

  @NonNull
  @JsonIgnore
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
  @JsonIgnore
  @OrderBy("created DESC")
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "participants")
  private final List<Match> matches = new LinkedList<>();

  @NonNull
  @JsonIgnore
  @OrderBy("created DESC")
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private final List<Code> codes = new LinkedList<>();

  //TODO Consider weather adding the one to many for guesses makes sense here.

  @NonNull
  public UUID getId() {
    return id;
  }

  @NonNull
  public String getKey() {
    return key;
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
  public Date getConnected() {
    return connected;
  }

  public void setConnected(@NonNull Date connected) {
    this.connected = connected;
  }

  @NonNull
  public List<Match> getMatchesOriginated() {
    return matchesOriginated;
  }

  @NonNull
  public List<Match> getMatches() {
    return matches;
  }

  @NonNull
  public List<Code> getCodes() {
    return codes;
  }

  @PrePersist
  private void setAdditionalFields() {
    UUID uuid = UUID.randomUUID();
    ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
    buffer.putLong(uuid.getMostSignificantBits());
    buffer.putLong(uuid.getLeastSignificantBits());
    key = ENCODER.encodeToString(buffer.array());
  }
}
