package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateTranCodeAction extends Action{

	public UpdateTranCodeAction() {
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SearchVO searchVO = new SearchVO();
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranNo(tranNo);
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		System.out.println("tranNo : " + tranNo);
		System.out.println("updateTronNo : "+purchaseVO.getTranNo());
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		
		HttpSession session = request.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		String buyer = user.getUserId();
		
		PurchaseService service2 = new PurchaseServiceImpl();
		HashMap<String, Object> map = service2.getPurchaseList(searchVO, buyer);
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		System.out.println("UpdateTranCodeAction map : "+map);
				
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
