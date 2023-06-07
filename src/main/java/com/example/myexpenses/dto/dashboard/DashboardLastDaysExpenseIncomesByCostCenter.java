package com.example.myexpenses.dto.dashboard;

import java.util.List;

import com.example.myexpenses.vo.TotalByCostCenterVo;

public class DashboardLastDaysExpenseIncomesByCostCenter {

    private List<TotalByCostCenterVo> expensesByCostCenter;

    private List<TotalByCostCenterVo> incomesByCostCenter;

	public DashboardLastDaysExpenseIncomesByCostCenter(List<TotalByCostCenterVo> expensesByCostCenter,
			List<TotalByCostCenterVo> incomesByCostCenter) {
		this.expensesByCostCenter = expensesByCostCenter;
		this.incomesByCostCenter = incomesByCostCenter;
	}

	public List<TotalByCostCenterVo> getExpensesByCostCenter() {
		return expensesByCostCenter;
	}

	public void setExpensesByCostCenter(List<TotalByCostCenterVo> expensesByCostCenter) {
		this.expensesByCostCenter = expensesByCostCenter;
	}

	public List<TotalByCostCenterVo> getIncomesByCostCenter() {
		return incomesByCostCenter;
	}

	public void setIncomesByCostCenter(List<TotalByCostCenterVo> incomesByCostCenter) {
		this.incomesByCostCenter = incomesByCostCenter;
	}
}