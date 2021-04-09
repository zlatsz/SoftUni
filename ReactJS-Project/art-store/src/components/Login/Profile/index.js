import "./index.css";
import Header from "../../Home/Navigation/Header";
import Footer from "../../Home/Footer";
import { AuthContext } from '../../../contexts/authentication';
import * as ordersService from '../../../services/ordersService';
import { useEffect, useState, useContext } from 'react';
import Order from "./Order";

const Profile = () => {
    const { currentUser } = useContext(AuthContext);
    const [userOrder, setUserOrder] = useState({});

    useEffect(() => {
        ordersService.getOrderUser(currentUser.email)
            .then(res => setUserOrder(res))
            .catch((error) => alert(error.message));
    }, [currentUser.email]);

    let result = Array.from(userOrder);
    
    return (
        <>
            <header className="home-header">
                <Header />
                <h2 className="blog-header-category-title">
                    Здравей, {currentUser.email}! Това са всички твои поръчки!
                     </h2>
            </header>
            <section className="users-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>
                                Поръчка
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
                        {result.map(x =>
                            <Order key={x.productId} {...x} />
                        )}
                    </tbody>
                </table>

            </section>
            <Footer />
        </>
    );

};

export default Profile;