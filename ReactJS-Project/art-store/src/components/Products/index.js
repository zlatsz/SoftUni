import "./index.css"
import { Component } from 'react';
import * as productsService from '../../services/productsService';
import Product from "../Product"

class Products extends Component {
    constructor(props) {
        super(props);

        this.state = {
            products: [],
            currentCategory: 'all',
        }
    }

    componentDidMount() {
        productsService.getAll()
            .then(res => this.setState({ products: res }))
    }


    render() {
        return (
            <section className="home-products">
                <ul className="home-products-list">
                    {/* {this.state.pets.map(x => 
                        <Product key={x.id} {...x} />
                    )} */}
                </ul>
            </section>
        );
    }
}

export default Products;
