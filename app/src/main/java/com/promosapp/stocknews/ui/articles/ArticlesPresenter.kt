/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.ui.articles

class ArticlesPresenter(var view: ArticlesView?) {

    fun onResume() {
//        view?.showProgress()
//        findItemsInteractor.findItems(::onItemsLoaded)
    }

    private fun onItemsLoaded(items: List<String>) {
        view?.apply {
//            setItems(items)
//            hideProgress()
        }
    }

    fun onItemClicked(item: String) {
//        mainView?.showMessage(item)
    }


    fun onDestroy() {
//        mainView = null
    }
}