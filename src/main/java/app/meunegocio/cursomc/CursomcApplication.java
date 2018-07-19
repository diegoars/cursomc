package app.meunegocio.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.meunegocio.cursomc.domain.Categoria;
import app.meunegocio.cursomc.domain.Cidade;
import app.meunegocio.cursomc.domain.Cliente;
import app.meunegocio.cursomc.domain.Endereco;
import app.meunegocio.cursomc.domain.Estado;
import app.meunegocio.cursomc.domain.Pagamento;
import app.meunegocio.cursomc.domain.PagamentoComBoleto;
import app.meunegocio.cursomc.domain.PagamentoComCartao;
import app.meunegocio.cursomc.domain.Pedido;
import app.meunegocio.cursomc.domain.Produto;
import app.meunegocio.cursomc.domain.enums.EstadoPagamento;
import app.meunegocio.cursomc.domain.enums.TipoCliente;
import app.meunegocio.cursomc.repositories.CategoriaRepository;
import app.meunegocio.cursomc.repositories.CidadeRepository;
import app.meunegocio.cursomc.repositories.ClienteRepository;
import app.meunegocio.cursomc.repositories.EnderecoRepository;
import app.meunegocio.cursomc.repositories.EstadoRepository;
import app.meunegocio.cursomc.repositories.PagamentoRepository;
import app.meunegocio.cursomc.repositories.PedidoRepository;
import app.meunegocio.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pegamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "10091806678", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("38316958", "956865242"));

		Endereco e1 = new Endereco(null, "Rua Chile", "245", "Esquina do beco", "Tibery", "38408208", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Patagonia", "235", "casa de grade", "Custódio Pereira", "38408208", cli1,
				c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("10/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pegamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

	}
}
