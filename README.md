# FxApi


5 endpoints

/currencyForDate/{currencyName}?date=yyyy-mm-dd

Gets currency exchange rate for the supplied date or if date is not supplied for current date

/exchange/EUR/{currencyName}?amount=xxx

Exchanges EUR to the supplied currency  

/exchange/{currencyName}/EUR?amount=xxx

Exchanges supplied currency to EUR

/currencyHistory/{currencyName}

Provides the history of supplied currency name
    
/populate

Populates h2 with recent values



Run:

mvn clean install spring-boot:run



