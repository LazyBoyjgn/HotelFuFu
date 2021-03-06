package top.jglo.hotel.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import top.jglo.hotel.model.FuHouse;
import top.jglo.hotel.model.FuHouseOpen;
import top.jglo.hotel.model.FuUser;
import top.jglo.hotel.model.result.ChartInfo;
import top.jglo.hotel.model.result.ChartIntInfo;
import top.jglo.hotel.repository.FuHouseOpenRepository;
import top.jglo.hotel.repository.FuHouseRepository;
import top.jglo.hotel.repository.FuRegisterRepository;
import top.jglo.hotel.repository.FuUserRepository;
import top.jglo.hotel.util.EntityUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jingening
 */
@Service
public class ChartService {
    @Resource
    private FuRegisterRepository fuRegisterRepository;
    @Resource
    private FuHouseOpenRepository fuHouseOpenRepository;
    @Resource
    private FuUserRepository fuUserRepository;


    public List<ChartInfo> getMonthSales(String year,int hotelId){
        List<ChartInfo> chartInfoList=new ArrayList<>();
        for(int i=1;i<=12;i++){
            Integer num= fuRegisterRepository.findMonSales(year+"-"+i+"-1",hotelId);
            if(num==null){
                num=0;
            }
            ChartInfo chartInfo=new ChartInfo(i+"月",String.valueOf(num));
            chartInfoList.add(chartInfo);
        }
        return chartInfoList;
    }

    public List<ChartInfo> getSeasonSales(String year,int hotelId){
        List<ChartInfo> chartInfoList=new ArrayList<>();
        String[] season={"春季","夏季","秋季","冬季"};
        Integer num=0;
        Integer sum=0;
        for(int i=1;i<=12;i++){
            num= fuRegisterRepository.findMonSales(year+"-"+i+"-1",hotelId);
            if(num==null){
                num=0;
            }
            sum+=num;
            if(i%3==0){
                chartInfoList.add(new ChartInfo(season[i/3-1],String.valueOf(sum)));
                sum=0;
            }
        }
        return chartInfoList;
    }

    public List<ChartInfo> getYearSales(String startYear,String endYear,int hotelId){
        List<ChartInfo> chartInfoList=new ArrayList<>();
        for(int y=Integer.valueOf(startYear);y<=Integer.valueOf(endYear);y++){
            int sum=0;
            for(int i=1;i<=12;i++){
                Integer num= fuRegisterRepository.findMonSales(y+"-"+i+"-1",hotelId);
                if(num==null){
                    num=0;
                }
                sum+=num;
            }
            ChartInfo chartInfo=new ChartInfo(y+"年",String.valueOf(sum));
            chartInfoList.add(chartInfo);
        }
        return chartInfoList;
    }

    public List<ChartInfo> findHouseSales(int hotelId) {
        ChartInfo chartInfo=new ChartInfo();
        List<Object[]> objects=fuRegisterRepository.findHouseSales(hotelId);
        List<ChartInfo>  chartInfoList=EntityUtil.castEntity(objects,ChartInfo.class,chartInfo);
        return chartInfoList;
    }
    public List<ChartInfo> findWeekSales(String date,int hotelId) {
        ChartInfo chartInfo=new ChartInfo();
        List<Object[]> objects=fuRegisterRepository.findWeekSales( date ,hotelId);
        List<ChartInfo>  chartInfoList=EntityUtil.castEntity(objects,ChartInfo.class,chartInfo);
        return chartInfoList;
    }

}
