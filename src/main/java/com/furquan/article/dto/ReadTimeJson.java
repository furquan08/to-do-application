package com.furquan.article.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "articleId", "timeToRead" })
public class ReadTimeJson {

	@JsonProperty("articleId")
	private String articleId;
	@JsonProperty("timeToRead")
	private TimeToRead timeToRead;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("articleId")
	public String getArticleId() {
		return articleId;
	}

	@JsonProperty("articleId")
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	@JsonProperty("timeToRead")
	public TimeToRead getTimeToRead() {
		return timeToRead;
	}

	@JsonProperty("timeToRead")
	public void setTimeToRead(TimeToRead timeToRead) {
		this.timeToRead = timeToRead;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
