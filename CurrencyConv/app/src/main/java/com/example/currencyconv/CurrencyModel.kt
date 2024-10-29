package com.example.currencyconv

data class CurrencyModel(
    val name: String,
    val iconResId: Int
) {
    companion object {
        fun getDefaultCurrencies(): List<CurrencyModel> = listOf(
            CurrencyModel("USD", R.drawable.us),
            CurrencyModel("EUR", R.drawable.eu),
            CurrencyModel("JPY", R.drawable.jp),
            CurrencyModel("VND", R.drawable.vn),
            CurrencyModel("KRW", R.drawable.kr),
            CurrencyModel("SAR", R.drawable.sa)

        )
    }
}
