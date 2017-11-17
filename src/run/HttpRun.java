package run;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import func.APITask;

public class HttpRun {

	public static void main(String...args) throws Exception {
		String url[] = {"https://openapi.kyobo.co.kr:1443/v1.0/NBI/contract/exception",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/accident/progress",
						"https://openapi.kyobo.co.kr:1443/v1.0/BIL/resurrection/target",
						"https://openapi.kyobo.co.kr:1443/v1.0/BIL/income/deduction",
						"https://openapi.kyobo.co.kr:1443/v1.0/BIL/premium/prediction",
						"https://openapi.kyobo.co.kr:1443/v1.0/BIL/premium/calc",
						"https://openapi.kyobo.co.kr:1443/v1.0/VAL/fund/subscription",
						"https://openapi.kyobo.co.kr:1443/v1.0/VAL/insurance/variable",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/accident/payment",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/loan/principal",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/withdraw/target",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/refund/cancellation",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/loan/acceptance",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/dividend/payment",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/loan/process",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/insurance/life",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/insurance/additive",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/withdraw/payment", 
						"https://openapi.kyobo.co.kr:1443/v1.0/NBI/contract/exception",
						"https://openapi.kyobo.co.kr:1443/v1.0/NBI/product/guarantee",
						"https://openapi.kyobo.co.kr:1443/v1.0/PAY/contract/terms",
						"https://openapi.kyobo.co.kr:443/app/rest/getConsumer"};
		
		String reqData[] = {"{\"dataBody\" : {\"NZ280UM_ELAG_INAG_NO\":\"197091040591\"}}",
							"{\"dataBody\" : {\"PN325UM_I_CLIM_CLIM_NO\" : \"20060803026973\"}}",
							"{\"dataBody\": {\"YI021UM_I_INAG_NO\":\"216023009510\"}}",
							"{\"dataBody\" : {\"YG041UM_I_INAG_INAG_NO\":\"214124025374\"}}",
							"{\"dataBody\" : {\"YD171UM_I_REG_NO\" : \"5704152871038\"}}",
							"{\"dataBody\" : {\"YD091UM_I_PRTY_REG_NO\":\"6006272613831\"}}",
							"{\"dataBody\": {\"VL240UM_I_INAG_NO\":\"214071028484\"	}}",
							"{\"dataBody\": {\"VG932FS_I_INAG_NO\":\"217065025183\"	}}",
							"{\"dataBody\": {\"PN065UM_I_PRTY_REG_NO\":\"5604172829932\"}}",
							"{\"dataBody\":{\"PE201UM_I_REG_NO\":\"6205072501633\"}}",
							"{\"dataBody\":{\"PC601UM_I_PRTY_REG_NO\":\"7010082661032\"	}}",
							"{\"dataBody\":{\"PC101UM_I_PRTY_REG_NO\":\"6103032205239\"	}}",
							"{\"dataBody\": {\"PB160UM_I_INAG_INAG_NO\":\"212077020022\",\"PB160UM_I_ACT_DT\":\"2013-09-20\"}}",
							"{\"dataBody\" :{\"PB150UM_I_YEAR\" : \"2008\",\"PB150UM_I_INAG_INAG_NO\":\"191020589815\",	\"PB150UM_I_ACT_DT\":\"2009-02-25\"	}}",
							"{\"dataBody\" : {\"PB130UM_I_INAG_INAG_NO\":\"201102366135\",\"PB130UM_I_ACT_DT\":\"2006-03-27\"}}",
							"{\"dataBody\" : {\"PB120UM_I_INAG_INAG_NO\":\"198072870297\",\"PB120UM_I_ACT_DT\":\"2002-01-08\"}}",
							"{\"dataBody\" : {\"PB018UM_PRTY_NO\":\"6301242190038\"	}}",
							"{\"dataBody\" : {\"PB081UM_I_INAG_INAG_NO\":\"205026021071\"}}",
							"{\"dataBody\" : {\"NZ280UM_ELAG_INAG_NO\":\"197091040591\"	}}",
							"{\"dataBody\" : {\"NF602UM_I_INAG_NO\":\"214040020487\"}}",
							"{\"dataBody\" : {\"PB020UM_I_INAG_INAG_NO\":\"200094117805\"}}",
							"{\"userId\":\"kkimdoy\",\"pwd\" : \"1\"}"};
		
		ExecutorService service = Executors.newFixedThreadPool(100);

		int size = url.length;
		IntStream.range(1, 100).forEach(i -> {
			IntStream.range(0, size).forEach(api -> {
				Future<String> future = service.submit(new APITask<String>(url[api], reqData[api]));
				try {
					System.out.println(future.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
		});
		
		service.shutdown();
		
		try {
			/** Wait a while for existing tasks to terminate **/
			if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
				service.shutdownNow();
			
				if (!service.awaitTermination(60,  TimeUnit.SECONDS)) {
					System.err.println("service did not terminated");
				}
			}			
		}
		catch(InterruptedException ie) {
			/** Recancel if current thread is also interrupted **/
			service.shutdown();
			
			Thread.currentThread().interrupt();
		}
	}
}
