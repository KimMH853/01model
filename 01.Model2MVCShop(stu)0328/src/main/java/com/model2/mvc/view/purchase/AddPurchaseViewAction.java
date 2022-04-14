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

public class AddPurchaseViewAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("에드액션뷰시작");
		
		  int prodNo = Integer.parseInt(request.getParameter("prodNo")) ;
		  
		  ProductService service=new ProductServiceImpl(); 
		  ProductVO vo = service.getProduct(prodNo);
		  
		  request.setAttribute("vo", vo);
		  
		 

		return "forward:/purchase/addPurchaseView.jsp";
	}

}
