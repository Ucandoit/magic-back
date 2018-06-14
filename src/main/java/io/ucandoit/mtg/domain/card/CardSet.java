package io.ucandoit.mtg.domain.card;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "set")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString(of = { "code" })
@EqualsAndHashCode(of = { "code" }, callSuper = false)
public class CardSet {

	@Id
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "release_date")
	private Date releaseDate;

}
