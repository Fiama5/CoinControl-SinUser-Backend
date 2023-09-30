package tup.CoinControlSinUserBackend.datainitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tup.CoinControlSinUserBackend.model.PredefinedFund;
import tup.CoinControlSinUserBackend.repository.PredefinedFundRepository;

@Component
public class FundsInitializer implements CommandLineRunner {

    private final PredefinedFundRepository predefinedFundRepository;

    @Autowired
    public FundsInitializer(PredefinedFundRepository predefinedFundRepository) {
        this.predefinedFundRepository = predefinedFundRepository;
    }

    @Override
    public void run(String... args) {
        if (predefinedFundRepository.count() == 0) {
            PredefinedFund fund1 = new PredefinedFund();
            fund1.setName("Salario");
            predefinedFundRepository.save(fund1);

            PredefinedFund fund2 = new PredefinedFund();
            fund2.setName("Ahorros");
            predefinedFundRepository.save(fund2);

            PredefinedFund fund3 = new PredefinedFund();
            fund3.setName("Inversiones");
            predefinedFundRepository.save(fund3);

            PredefinedFund fund4 = new PredefinedFund();
            fund4.setName("Otros");
            predefinedFundRepository.save(fund4);

        }
    }

}
