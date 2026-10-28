import java.util.List;
import java.util.Locale;
 
public class lojaOnline {
 
    public static void main(String[] args) {
        List<Item> itens = List.of(
                new Item("Teclado", 150.00, 1),
                new Item("Mouse", 50.00, 2));
 
        Carrinho carrinhoVip = new Carrinho(itens, true);
        Carrinho carrinhoComum = new Carrinho(itens, false);
 
        System.out.println(new EmailPromocional().mensagem("Lucas"));
        System.out.println(String.format(Locale.US, "Total do carrinho VIP: R$ %.2f", carrinhoVip.total()));
        System.out.println(new NotaFiscal().gerar(carrinhoVip));
        System.out.println(String.format(Locale.US, "Economia total dos VIPs: R$ %.2f",
                new Relatorio().economiaDosClientesVip(List.of(carrinhoVip, carrinhoComum))));
    }
}
 
class Item {
    final String nome;
    final double preco;
    final int qtd;
 
    Item(String nome, double preco, int qtd) {
        this.nome = nome;
        this.preco = preco;
        this.qtd = qtd;
    }
}
 
class Carrinho {
    final List<Item> itens;
    final boolean clienteVip;
 
    Carrinho(List<Item> itens, boolean clienteVip) {
        this.itens = itens;
        this.clienteVip = clienteVip;
    }
 
    double subtotal() {
        double soma = 0;
        for (Item item : itens) {
            soma += item.preco * item.qtd;
        }
        return soma;
    }
 
    double total() {
        double total = subtotal();
        if (clienteVip) {
            total = total * 0.90; 
        }
        return total;
    }
}
 
class NotaFiscal {
    String gerar(Carrinho carrinho) {
        double subtotal = carrinho.subtotal();
        double desconto = 0;
        if (carrinho.clienteVip) {
            desconto = subtotal * 0.10; 
        }
        double total = subtotal - desconto;
        return String.format(Locale.US,
                "NOTA FISCAL | Subtotal: R$ %.2f | Desconto VIP (10%%): R$ %.2f | Total: R$ %.2f",
                subtotal, desconto, total);
    }
}
 
class Relatorio {
    double economiaDosClientesVip(List<Carrinho> carrinhos) {
        double economia = 0;
        for (Carrinho carrinho : carrinhos) {
            if (carrinho.clienteVip) {
                economia += carrinho.subtotal() * 0.10; 
            }
        }
        return economia;
    }
}
 
class EmailPromocional {
    String mensagem(String nome) {
        return "Olá, " + nome + "! Como cliente VIP, você tem 10% de desconto em todas as compras.";
    }
}