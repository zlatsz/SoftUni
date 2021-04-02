import { Link } from 'react-router-dom';
import { useEffect, useState, useContext } from 'react';
import Header from "../Home/Navigation/Header/";
import Footer from "../Home/Footer";
import "./index.css";

const Cart = ({
    match,
    history

}) => {

    return (
        <>
        <header className="home-header">
            <Header />
            <h3 className="home-header-category-title">
                Всички наши продукти се изработват ръчно от естествени съставки и материали!
                 </h3>
        </header>
        <Footer />
        </>
        );
}

export default Cart;