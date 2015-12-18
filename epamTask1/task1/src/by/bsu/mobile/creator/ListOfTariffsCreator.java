package by.bsu.mobile.creator;

import by.bsu.mobile.entity.*;
import by.bsu.mobile.init.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class ListOfTariffsCreator {
        static Logger logger = Logger.getLogger(ListOfTariffsCreator.class);

        public static ArrayList<AbstractTariff> createList() {
                ArrayList<AbstractTariff> list = new ArrayList<>();
                BusinessInitializer businessInitializer = new BusinessInitializer();
                Business business = new Business();
                businessInitializer.initialize(business, "task1/resources/business.txt");
                logger.info("Business has been created");
                list.add(business);
                BusinessProInitializer businessProInitializer = new BusinessProInitializer();
                BusinessPro businessPro = new BusinessPro();
                businessProInitializer.initialize(businessPro, "task1/resources/businesspro.txt");
                logger.info("BusinessPro has been created");
                list.add(businessPro);
                BusinessClassInitializer businessClassInitializer = new BusinessClassInitializer();
                BusinessClass businessClass = new BusinessClass();
                businessClassInitializer.initialize(businessClass, "task1/resources/businessclass.txt");
                logger.info("BusinessClass has been created");
                list.add(businessClass);
                EverybodyTalksInitializer everybodyTalksInitializer = new EverybodyTalksInitializer();
                EverybodyTalks everybodyTalks = new EverybodyTalks();
                everybodyTalksInitializer.initialize(everybodyTalks, "task1/resources/everybodytalks.txt");
                logger.info("EverybodyTalks has been created");
                list.add(everybodyTalks);
                GumshoesInitializer gumshoesInitializer = new GumshoesInitializer();
                Gumshoes gumshoes = new Gumshoes();
                gumshoesInitializer.initialize(gumshoes, "task1/resources/gumshoes.txt");
                logger.info("Gumshoes has been created");
                list.add(gumshoes);
                InConnectionInitializer inConnectionInitializer = new InConnectionInitializer();
                InConnection inConnection = new InConnection();
                inConnectionInitializer.initialize(inConnection, "task1/resources/inconnection.txt");
                logger.info("InConnection has been created");
                list.add(inConnection);
                ShoesInitializer shoesInitializer = new ShoesInitializer();
                Shoes shoes = new Shoes();
                shoesInitializer.initialize(shoes, "task1/resources/shoes.txt");
                logger.info("Shoes has been created");
                list.add(shoes);
                SmartTariffInitializer smartTariffInitializer = new SmartTariffInitializer();
                SmartTariff smart1 = new SmartTariff();
                smartTariffInitializer.initialize(smart1, "task1/resources/smart1.txt");
                logger.info("SmartTariff has been created");
                list.add(smart1);
                SmartTariff smart2 = new SmartTariff();
                smartTariffInitializer.initialize(smart2, "task1/resources/smart2.txt");
                logger.info("SmartTariff has been created");
                list.add(smart2);
                SmartTariff smart3 = new SmartTariff();
                smartTariffInitializer.initialize(smart3, "task1/resources/smart3.txt");
                logger.info("SmartTariff has been created");
                list.add(smart3);
                SummerCallsInitializer summerCallsInitializer = new SummerCallsInitializer();
                SummerCalls summerCalls = new SummerCalls();
                summerCallsInitializer.initialize(summerCalls, "task1/resources/summercalls.txt");
                logger.info("SummerCalls has been created");
                list.add(summerCalls);
                return list;
        }
}
