import { Link } from 'react-router-dom';
import { useEffect, useState} from 'react';
import * as ordersService from "../../../services/ordersService"
import Header from "../../Home/Navigation/Header";
import Footer from "../../Home/Footer";
import OrderView from "../OrderView";
import "./index.css";

const OldCart = ({
    match,
}) => {

    const [oldOrder, setOldOrder] = useState({});

    useEffect(() => {
        ordersService.getOrder(match.params.orderId)
            .then(res => setOldOrder(res))
            .catch((error) => alert(error.message));
    }, [match.params.orderId]);
    let data = Array.from(oldOrder);
    var result = data.reduce(function(tot, arr) { 
        return tot + arr.totalPrice;
      },0);

    return (
        <>
            <header className="home-header">
                <Header />
                <h3 className="home-header-category-title">
                    Стара поръчка! Благодарим, че пазарувахте при нас!
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
                            <th>
                                Сума
                </th>
                        </tr>
                    </thead>
                    <tbody >
                        {data.map(x =>
                            <OrderView key={x.id} {...x} />
                        )}
                        <tr>
                            <td className="total-sum" colSpan="5">Обща сума: <b>{result}</b> лв.</td>
                        </tr>
                    </tbody>

                </table>
                <Link to="/home" className="cart-button-wrapper"><button type="button" className="old-cart-button">Пазараувай пак!</button></Link>
               
            </section>
            <Footer />
        </>
    );
}

export default OldCart;