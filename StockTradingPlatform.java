import java.util.*;
class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;

    }
    public String getSymbol() {
        return symbol;

    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
class Market {
    private Map< String, Stock > stocks;
    public Market() {
        stocks = new HashMap<>();
        stocks.put("AAPL", new Stock("AAPL", 150.0));
        stocks.put("GOOGL", new Stock("GOOGL", 2800.0));
        stocks.put("AMZN", new Stock("AMZN", 3400.0));

    }
    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }
    public void updatePrices() {
        Random random = new Random();
        for( Stock stock : stocks.values()) {
            double change = (random.nextDouble() -0.5) * 10;
            stock.setPrice(stock.getPrice() + change);

        }
    }
    public void displayMarketData() {
        for(Stock stock : stocks.values()) {
            System.out.println(stock.getSymbol() + ":$" + stock.getPrice());
        }
    }

}
class Portfolio {
    private Map <String, Integer > holdings;
    private double cashBalance;
    public Portfolio(double initialBalance) {
        holdings = new HashMap<>();
        cashBalance = initialBalance;
    }
    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cashBalance >= cost) {
            cashBalance -= cost;
            holdings.put(stock.getSymbol(),holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("Bought" + quantity + "shares of" + stock.getSymbol());
        } else {
            System.out.println("Not enough balance to buy" + quantity + "shares of" + stock.getSymbol());
            
        }
    }
    public void sellStock(Stock stock, int quantity) {
        if (holdings.getOrDefault(stock.getSymbol(), 0) >= quantity) {
            holdings.put(stock.getSymbol(), holdings.get(stock.getSymbol()) - quantity);
            cashBalance += stock.getPrice() * quantity;
            System.out.println("Sold" + quantity + "shares of" + stock.getSymbol());

        } else {
            System.out.println("Not enough shares to sell" + quantity + "shares of" + stock.getSymbol());
            
        }
    }
    public void displayPortfolio() {
        System.out.println("Cash Balance:$" + cashBalance);
        System.out.println("Holdings:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue() + "shares");
            
        }
    }
}
public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Market market = new Market();
        Portfolio portfolio = new Portfolio(10000);
        while(true) {
            System.out.println("\n1. Display Market Data");
            System.out.println("\n2. Buy Stock");
            System.out.println("\n3. Sell Stock");
            System.out.println("\n4. Display Portfolio");
            System.out.println("\n5. Exit");
            System.out.println("Choose an option:");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                market.updatePrices();
                market.displayMarketData();
                    break;
                    case 2:
                    System.out.print("Enter stock symbol to buy:");
                    String buySymbol = scanner.next();
                    System.out.print("Enter quantity to buy:");
                    int buyQuantity = scanner.nextInt();
                    Stock stockToBuy = market.getStock(buySymbol);
                    if (stockToBuy != null) {
                        portfolio.buyStock(stockToBuy, buyQuantity);

                    } else {
                        System.out.println("Stock not found!");
                        
                    }
                    break;
                    case 3:
                    System.out.print("Enter stock symbol to sell:");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter quantity to sell:");
                    int sellQuantity = scanner.nextInt();
                    Stock stockToSell = market.getStock(sellSymbol);
                    if (stockToSell != null) {
                        portfolio.sellStock(stockToSell, sellQuantity);
    
                    } else {
                        System.out.println("Stock not found!");
                        
                    }
                    break;
                    case 4:
                    portfolio.displayPortfolio();
                    break;
                    case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return; 
                default:
                System.out.println("Invalid choice! Please try again.");
                    
            }
        }
    }
}