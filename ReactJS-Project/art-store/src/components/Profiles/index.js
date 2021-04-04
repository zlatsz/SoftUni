import "./index.css";
import Header from "../Home/Navigation/Header";
import Footer from "../Home/Footer";
import Unique from "./Unique";
import * as ordersService from '../../services/ordersService';
import { useEffect, useState } from 'react';

const Profiles = ({
    match,
    history

}) => {
    const [usersOrder, setUsersOrder] = useState({});

    useEffect(() => {
        ordersService.getAll()
            .then(res => setUsersOrder(res));
    }, []);

    let data = Array.from(usersOrder);
    return (
        <>
            <header className="home-header">
                <Header />
                <h2 className="blog-header-category-title">
                    РЕГИСТРИРАНИ ПОТРЕБИТЕЛИ И ПОРЪЧКИ
                     </h2>
            </header>
            <section className="users-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>
                               Потребител
                </th>
                            <th>
                               Брой продукти
                </th>
                            <th>
                                Количество
                </th>
                            <th>
                                Сума
                </th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map(x =>
                            <Unique key={x.id} {...x} />
                        )}
                    </tbody>
                </table>

            </section>
            <Footer />
        </>
    );

};

export default Profiles;