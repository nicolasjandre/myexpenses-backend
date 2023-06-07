package com.example.myexpenses.vo;

public class TotalByCostCenterVo {

    private Long id;

    private String costCenter;

    private Double total;

	public TotalByCostCenterVo() {
	}

	public TotalByCostCenterVo(Long id, String costCenter, Double total) {
		this.id = id;
		this.costCenter = costCenter;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
