import "./index.css";
import Header from "../Home/Navigation/Header";
import Footer from "../Home/Footer";
import Unique from "./Unique";
import * as userService from '../../services/userService';
import { useEffect, useState } from 'react';

const Profiles = ({
    match,
    history

}) => {
    const [users, setUsers] = useState({});

    useEffect(() => {
        userService.getAll()
            .then(res => setUsers(res));
    }, []);

    return (
        <>
            <header className="home-header">
                <Header />
                <h2 className="blog-header-category-title">
                    РЕГИСТРИРАНИ ПОТРЕБИТЕЛИ И ПОРЪЧКИ
                     </h2>
            </header>
            <section className="users-wrapper">
                <ul className="users-wrapper-items">
                    {users.map(x =>
                        <Unique key={x.id} {...x} />
                    )}
                </ul>

            </section>
            <Footer />
        </>
    );

};

export default Profiles;