package com.ninjaone.backendinterviewproject.controller.domain;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MontlyStatementResponse {

	private Period period;
	private List<Statement> statements;
	private BigDecimal totalPrice;

	@Data
	@Builder
	public static class Statement {

		private String date;
		private String deviceDescription;
		private String serviceDescription;
		private BigDecimal price;
	}
}
