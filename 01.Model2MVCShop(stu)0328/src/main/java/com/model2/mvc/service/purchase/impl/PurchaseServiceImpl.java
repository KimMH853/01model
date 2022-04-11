package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;

public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO=new PurchaseDAO();
	}


	@Override
	public void addPurchase(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.insertPurchase(purchaseVO);

	}

	@Override
	public PurchaseVO getPurchase(int prodNo) throws Exception {
		return  null;//purchaseDAO.getPurchase(prodNo);
	}

	@Override
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO) throws Exception {
		return null;//purchaseDAO.getPurchaseList(searchVO);
	}

	@Override
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		//purchaseDAO.updatePurchase(purchaseVO);

	}

}
