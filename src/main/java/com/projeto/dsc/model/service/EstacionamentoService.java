package com.projeto.dsc.model.service;

import com.projeto.dsc.model.entity.Cliente;
import com.projeto.dsc.model.entity.ClienteVaga;
import com.projeto.dsc.model.entity.Vaga;
import com.projeto.dsc.model.enums.StatusVaga;
import com.projeto.dsc.ultis.EstacionamentoUltis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static com.projeto.dsc.ultis.EstacionamentoUltis.gerarReivo;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {



    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;

    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga){
        Cliente cliente =  clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
        clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.buscarVagaLivre();
        vaga.setStatus(StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga);

        clienteVaga.setDataEntrada(new Date());
        clienteVaga.setRecibo(gerarReivo());

        return clienteVagaService.save(clienteVaga);

    }
    @Transactional
    public ClienteVaga checkOut(String recibo) {
        ClienteVaga vaga = clienteVagaService.buscarPorRecibo(recibo);

        Date dataSaida = new Date();

        BigDecimal vagaValor = EstacionamentoUltis.calcularCustos(vaga.getDataEntrada(),dataSaida);
        vaga.setValor(vagaValor);
        vaga.setDataSaida(dataSaida);
        vaga.getVaga().setStatus(StatusVaga.LIVRE);
        return clienteVagaService.save(vaga);
    }
}
