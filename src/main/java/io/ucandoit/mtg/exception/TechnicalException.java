package io.ucandoit.mtg.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class TechnicalException extends RuntimeException {

	private static final long serialVersionUID = -1557622717357452724L;
	private TechnicalErrorCode error;
	private int httpCode;
	private String content;

	public TechnicalException(TechnicalErrorCode code) {
		super();
		this.error = code;
	}

	public TechnicalException(TechnicalErrorCode code, Throwable cause) {
		super(cause);
		this.error = code;
	}

	public TechnicalException(TechnicalErrorCode code, String message) {
		super(message);
		this.error = code;
	}

	public TechnicalException(TechnicalErrorCode code, int httpCode, String content) {
		super();
		this.error = code;
		this.httpCode = httpCode;
		this.content = content;
	}

	public TechnicalException(TechnicalErrorCode code, String message, Throwable cause) {
		super(message, cause);
		this.error = code;
	}

}
