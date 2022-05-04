package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

import sun.security.krb5.internal.LastReqEntry;

public class UpdateTranCodeByProdAction extends Action{

	public UpdateTranCodeByProdAction() {
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SearchVO searchVO = new SearchVO();
		int page=1;
		if(request.getParameter("page") != null)
			page=Integer.parseInt(request.getParameter("page"));
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranNo(prodNo);
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		System.out.println("UpdateTranCodeByProdAction prodNo : "+prodNo);
		System.out.println("UpdateTranCodeByProdAction tranCode : "+purchaseVO.getTranCode());
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		
		ProductService service2 = new ProductServiceImpl();
		HashMap<String,Object> map = service2.getProductList(searchVO);
		
		request.setAttribute("map", searchVO);
		
		return "forward : /listProduct.do?menu=manage";
	}
	
	

}
