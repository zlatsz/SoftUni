import "./index.css"
import { Component } from 'react';
import * as productsService from '../../../services/productsService';
import Product from "../../Product/ProductView";
import Header from "../../Home/Navigation/Header";
import Footer from "../../Home/Footer";

class Products extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products: [],
            currentCategory: '',
        }
    }

    componentDidMount() {
        const category = this.props.match.params.category;

        productsService.getCategory(category)
            .then(res => this.setState({ products: res }))
            .catch((error) => alert(error.message));

    }

    componentDidUpdate(prevProps) {
        const category = this.props.match.params.category;
      
        if (prevProps.match.params.category === category) {
            return;
        }

        productsService.getCategory(category)
            .then(res => {
                this.setState({ products: res, currentCategory: category })
            })
            .catch((error) => alert(error.message));
    }

    render() {
      
        return (
            <>
                <header className="home-header">
                    <Header />
                    <h3 className="home-header-category-title">
                        Всички наши продукти се изработват ръчно от естествени съставки и материали!
                     </h3>
                </header>
                <section className="home-products">
                    <ul className="home-products-list">
                        
                        {this.state.products.map(x =>
                            <Product key={x.id} {...x} />
                        )}
                        
                    </ul>
                </section>
                <Footer />
            </>
        );
    }
}

export default Products;
