import { Link } from 'react-router-dom';
import { useEffect, useState, useContext } from 'react';
import * as ordersService from "../../services/ordersService"
import { CartContext } from '../../contexts/shoppingCart';
import Header from "../Home/Navigation/Header/";
import Footer from "../Home/Footer";
import OrderView from "./OrderView";
import "./index.css";

const Cart = ({
    match,
    history

}) => {

    const { cart } = useContext(CartContext);
    const [order, setOrder] = useState({});

    useEffect(() => {
        ordersService.getOrder(cart.name)
            .then(res => setOrder(res));
    }, [cart.name]);
    let data = Array.from(order);
    console.log(order);
    return (
        <>
            <header className="home-header">
                <Header />
                <h3 className="home-header-category-title">
                    Благодарим, че пазарувате от нас!
                 </h3>
            </header>
            <section className="cart-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>
                                Име
                </th>
                            <th>
                                Продукт
                </th>
                            <th>
                                Количество
                </th>
                            <th>
                                Цена
                </th>
                        </tr>
                    </thead>
                    <tbody>
                    {data.map(x =>
                        <OrderView key={x.id} {...x} />
                    )}
                    </tbody>
                </table>

            </section>
            <Footer />
        </>
    );
}

export default Cart;