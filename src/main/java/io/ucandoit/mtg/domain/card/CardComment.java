package io.ucandoit.mtg.domain.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "comment")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString(of = { "id", "type", "value" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class CardComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "type")
	private String type;

	@Column(name = "value")
	private double value;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_card")
	private Card card;

}
