import "./index.css"
import { Component } from 'react';
import { Link } from 'react-router-dom';
import Footer from "../Landing-page/Footer"

class Login extends Component {
    state = {
        email: '',
        password: '',
    };

    changeHandler(event) {
        this.setState({ value: event.target.value });
    }
    submitHandler(event) {
        event.preventDefault();
        // Doing some AJAX with the data...
    }


    render() {
        const { email, password } = this.state;
        return (
            <>
                <section className="login-page">
                    <Link to="/" className="login-logo">
                        <img src="logo.png" alt="logo" />
                    </Link>
                    <article className="login-page-wrapper">
                        <h2>Влез в профила си:</h2>
                        <form className="login-page-content" onSubmit={this.submitHandler}>
                            <i className="fas fa-envelope fa-lg" aria-hidden="true"></i>
                            <input type="text" placeholder="Email" value={email} onChange={this.changeHandler} />

                            <i className="fa fa-lock fa-lg" aria-hidden="true"></i>
                            <input type="password" placeholder="Password" value={password} onChange={this.changeHandler} />

                            <button type="submit" className="login-page-submit" value="">Влез</button>
                        </form>
                        <h3>Регистрирай се <Link to="/register"><span>ТУК</span></Link></h3>
                    </article>
                </section>
                <Footer />
            </>
        );
    };

};

export default Login;