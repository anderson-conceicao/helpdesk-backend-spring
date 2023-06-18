package com.anderson.helpdesk.service.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anderson.helpdesk.domain.Chamado;
import com.anderson.helpdesk.domain.Cliente;
import com.anderson.helpdesk.domain.Tecnico;
import com.anderson.helpdesk.domain.dtos.ChamadoDTO;
import com.anderson.helpdesk.repositories.ChamadoRepository;
import com.anderson.helpdesk.services.ChamadoService;
import com.anderson.helpdesk.services.ClienteService;
import com.anderson.helpdesk.services.TecnicoService;
import com.anderson.helpdesk.services.exceptions.ObjectnotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ChamadoService.class})
public class ChamadoServiceTest {

    @Mock
    private ChamadoRepository repository;
    @Mock
    private TecnicoService tecnicoService;
    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ChamadoService chamadoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Chamado chamado = new Chamado();
        chamado.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(chamado));

        Chamado result = chamadoService.findById(id);

        assertEquals(chamado, result);
    }

    @Test(expected = ObjectnotFoundException.class)
    public void testFindByIdNotFound() {
        Integer id = 1;

        when(repository.findById(id)).thenReturn(Optional.empty());

        chamadoService.findById(id);
    }

    @Test
    public void testFindAll() {
        List<Chamado> chamados = new ArrayList<>();
        chamados.add(new Chamado());
        chamados.add(new Chamado());

        when(repository.findAll()).thenReturn(chamados);

        List<Chamado> result = chamadoService.findAll();

        assertEquals(chamados, result);
    }

    @Test
    public void testCreate() {
        ChamadoDTO obj = new ChamadoDTO();
        obj.setTecnico(1);
        obj.setCliente(1);

        Tecnico tecnico = new Tecnico();
        Cliente cliente = new Cliente();

        when(tecnicoService.findById(obj.getTecnico())).thenReturn(tecnico);
        when(clienteService.findById(obj.getCliente())).thenReturn(cliente);
        when(repository.save(any(Chamado.class))).thenReturn(new Chamado());

        Chamado result = chamadoService.create(obj);

        verify(repository, times(1)).save(any(Chamado.class));
        assertEquals(tecnico, result.getTecnico());
        assertEquals(cliente, result.getCliente());
    }

    @Test
    public void testUpdate() {
        Integer id = 1;
        ChamadoDTO objDTO = new ChamadoDTO();
        objDTO.setId(id);
        objDTO.setStatus(2);

        Chamado oldObj = new Chamado();

        when(repository.findById(id)).thenReturn(Optional.of(oldObj));
        when(repository.save(any(Chamado.class))).thenReturn(oldObj);

        Chamado result = chamadoService.update(id, objDTO);

        verify(repository, times(1)).save(any(Chamado.class));
        assertEquals(id, result.getId());
        assertEquals(LocalDate.now(), result.getDataFechamento());
        // Verifique outros atributos atualizados se necessário
    }

    @Test(expected = ObjectnotFoundException.class)
    public void testUpdateNotFound() {
        Integer id = 1;
        ChamadoDTO objDTO = new ChamadoDTO();
        objDTO.setId(id);

        when(repository.findById(id)).thenReturn(Optional.empty());

        chamadoService.update(id, objDTO);
    }
}
