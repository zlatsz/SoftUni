import "./index.css"
import { Link, NavLink } from 'react-router-dom';
import { Component } from 'react';
// import AddProducts from "../../AddProducts"
// import Profiles from "../../Profiles"

class Header extends Component {
    state = {
        search: '',
    }
    render() {
        const { search } = this.state;
        return (
            <section className="header-navigation-wrapper">
                <section className="header-navigation-upper">
                    <h3>Sisters Art Store</h3>
                    <Link to="/home" className="header-home-logo">
                        <img src="logo.png" alt="logo" />
                    </Link>
                    <form className="home-search" onSubmit={this.submitHandler}>
                        <i class="fas fa-phone-volume">0888808808</i>
                        <input type="text" value={search} onChange={this.changeHandler} />

                        <button type="submit" className="home-search-btn"><i class="fa fa-search fa-lg"></i></button>

                    </form>

                </section>
                <nav className="header-navigation-bottom">
                    <ul className="header-navigation-bottom-left">
                        <li className="header-navigation-bottom-left-item">
                            <NavLink to="/products/all" className="header-navigation-bottom-left-title"> Продукти </NavLink>
                            <ul className="header-navigation-bottom-left-sub">
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/jewellery">Бижута</NavLink>
                                </li>
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/cosmetics">Козметика</NavLink>
                                </li>
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/carts">Картички</NavLink>
                                </li>
                                <li className="header-navigation-bottom-left-sub-item ">
                                    <NavLink to="/products/more">Още...</NavLink>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <NavLink to="/about-us"> За нас </NavLink>
                        </li>
                        <li>
                            <NavLink to="/blog"> Блог </NavLink>
                        </li>
                    </ul>
                    <ul className="header-navigation-bottom-middle">
                        <li className="header-navigation-bottom-middle-products">
                             <a> Добави <i class="fa fa-caret-down" aria-hidden="true"></i></a>
                             <ul className="header-navigation-bottom-middle-sub">
                                <li className="header-navigation-bottom-middle-sub-item ">
                                    <NavLink to="/add-products">Добави продукт</NavLink>
                                </li>
                                <li className="header-navigation-bottom-middle-sub-item ">
                                    <NavLink to="/add-posts">Добави статия</NavLink>
                                </li>
                            </ul>
                        </li>
                        <li className="header-navigation-bottom-middle-profiles">
                            <NavLink to="/profiles"> Потребители </NavLink>
                        </li>
                    </ul>
                    <ul className="header-navigation-bottom-right">
                        <li className="header-navigation-bottom-right-profile">
                            <NavLink to="/profile"> <i class="fas fa-user fa-lg" /></NavLink>
                        </li>
                        <li className="header-navigation-bottom-right-profile">
                            <NavLink to="/cart"> <i class="fas fa-shopping-cart"></i></NavLink>
                        </li>
                        <li className="header-navigation-bottom-right-profile">
                            <NavLink to="/logout"> <i class="fas fa-sign-out-alt"></i></NavLink>
                        </li>
                    </ul>
                </nav>


            </section>
        );
    };
};

export default Header;