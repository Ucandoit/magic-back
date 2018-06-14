package io.ucandoit.mtg.domain.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "card")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString(of = { "id", "name", "rarity" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Card {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "rarity")
	private String rarity;

	@Column(name = "image_url")
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_set")
	private CardSet cardSet;

}
