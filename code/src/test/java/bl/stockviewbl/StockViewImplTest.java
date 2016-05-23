package bl.stockviewbl;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.*;

import data.stockdata.CacheDataTxtImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import bl.stockmanagebl.StockManageImpl;
import data.factory.DataFactoryTxtImpl;
import dataservice.stockdataservice.CacheDataService;
import tool.constant.ConditionSelect;
import tool.exception.BadInputException;
import tool.exception.NotFoundException;
import vo.chart.common.StockVO;

public class StockViewImplTest {

	static CacheDataService dataservice;
	static StockManageImpl stockmanage;
	static StockViewImpl stockview;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stockmanage = new StockManageImpl();
		stockview = new StockViewImpl();
		dataservice = new CacheDataTxtImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test

	public void testGetAllStock() throws NotFoundException, BadInputException {

		List<StockVO> stocks = stockview.getAllStock("2016", "sz", "2015-11-07");
		for(StockVO vo: stocks){
			vo.show();
		}
	}

	@Test

	public void testGetStock() throws NotFoundException, BadInputException {

		String field = null;
		try {
			field = dataservice.getAllFields();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ConditionSelect> ranges = new ArrayList<ConditionSelect>();
		ranges.add(new ConditionSelect("high", "4.4", "4.6"));
		ranges.add(new ConditionSelect("low", "4.2", "4.35"));
//		StockVO vo = stockview.getStock("sh600000", "2015-02-02", "2015-02-15", field, ranges);
//		vo.show();
	}
	
	@Test
	public void testHashMap(){
		HashMap<String,String> xx = new HashMap<String,String>();
		System.out.println(xx.get("123"));
	}

	@Test

	public void testGetAllBenchmark() throws NotFoundException, BadInputException {

		List<StockVO> vos = stockview.getAllBenchmark("2016-02-19");
		for(StockVO vo: vos){
			vo.show();
		}
	}

	@Test

	public void testGetBenchmark() throws NotFoundException, BadInputException {

		String fields = null;
		try {
			fields = dataservice.getBenchFields();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StockVO vo = stockview.getBenchmark("hs300", "2016-02-20", "2016-02-21", fields,new ArrayList<>());
		vo.show();
	}

	@Test

	public void testgetAllFields(){
		List<String> fields = stockview.getAllFields();
		for(String s: fields){
			System.out.println(s);
		}
	}

	@Test

	public void testgetBenchFields(){
		List<String> fields = stockview.getBenchFields();
		for(String s: fields){
			System.out.println(s);
		}
	}

	@Test

	public void testgetNamesAndNumbers(){
		HashMap<String, HashMap<String, String>> names = stockview.getNamesAndNumbers();

		//遍历行业Map
		Iterator<Map.Entry<String, HashMap<String, String>>> iter = names.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = iter.next();
			String occupation = (String) entry.getKey();

			System.out.println(occupation+":");

			HashMap<String, String> stocksInOne = (HashMap<String, String>) entry.getValue();
			//遍历行业内股票Map
			Iterator<Map.Entry<String, String>> subiter = stocksInOne.entrySet().iterator();

			while (subiter.hasNext()) {
				Map.Entry subentry = subiter.next();

				//根据股票名称新建对应button
				String singleName = (String) subentry.getKey();
				System.out.println(singleName);


			}
			System.out.println();

		}

	}

	@Test

	public void testgetBenchNamesAndNumbers(){

		HashMap<String, HashMap<String, String>> names = stockview.getBenchNamesAndNumbers();

		//遍历行业Map
		Iterator<Map.Entry<String, HashMap<String, String>>> iter = names.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = iter.next();
			String occupation = (String) entry.getKey();

			System.out.println(occupation+":");

			HashMap<String, String> stocksInOne = (HashMap<String, String>) entry.getValue();
			//遍历行业内股票Map
			Iterator<Map.Entry<String, String>> subiter = stocksInOne.entrySet().iterator();

			while (subiter.hasNext()) {
				Map.Entry subentry = subiter.next();

				//根据股票名称新建对应button
				String singleName = (String) subentry.getKey();
				System.out.println(singleName);


			}
			System.out.println();

		}

	}

}
