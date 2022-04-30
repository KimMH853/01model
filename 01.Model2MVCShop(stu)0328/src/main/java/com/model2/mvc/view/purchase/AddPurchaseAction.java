package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action{


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		  
		ProductService service=new ProductServiceImpl(); 
		ProductVO productvo = service.getProduct(prodNo);
		  
		String userId=request.getParameter("buyerId");
			
		UserService userservice = new UserServiceImpl();
		UserVO uservo = userservice.getUser(userId);
				
		PurchaseVO purchaseVO=new PurchaseVO();
		
		purchaseVO.setPurchaseProd(productvo);
		purchaseVO.setBuyer(uservo);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));		
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		purchaseVO.setTranCode("1");
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.addPurchase(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		
		return"forward:/purchase/addPurchase.jsp";
	}

}
