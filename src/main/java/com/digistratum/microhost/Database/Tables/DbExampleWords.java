package com.digistratum.microhost.Database.Tables;

import javax.persistence.*;

/*
CREATE TABLE `words` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`word` varchar(25) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
*/
@Entity
@Table(name = "words")
public class DbExampleWords {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "word")
	private String word;

	public DbExampleWords() {

	}

	public DbExampleWords(Integer id, String word) {
		this.id = id;
		this.word = word;
	}

	public DbExampleWords(String word) {
		this.word = word;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return "{\"id\":"+id+",\"word\":\""+word+"\"}";
	}
}
