package com.example.clothesshop

class ProvideData {
    companion object{
        fun getData():List<ProductViewModel>{
            var listProductViewModel = mutableListOf<ProductViewModel>()
            listProductViewModel.add(
                ProductViewModel(
                    R.drawable.ic_baseline_shop_24,
                    name = "Kurtka damska MERIDA beżowa Dstreet",
                    code = "TY2961",
                    price = "189,99 zł"
                )
            )
            listProductViewModel.add(
                ProductViewModel(
                    R.drawable.ic_baseline_shop_24,
                    name = "Kurtka męska skórzana czarna Dstreet",
                    code = "TX4246",
                    price = "159,99 zł"
                )
            )
            listProductViewModel.add(
                ProductViewModel(
                    R.drawable.ic_baseline_shop_24,
                    name = "Kurtka męska granatowa Dstreet",
                    code = "TX4119",
                    price = "159,99 zł"
                )
            )
            listProductViewModel.add(
                ProductViewModel(
                    R.drawable.ic_baseline_shop_24,
                    name = "Kurtka męska skórzana czarna Dstreet",
                    code = "TX3464",
                    price = "89,99 zł"
                )
            )




            return listProductViewModel
        }
    }
}