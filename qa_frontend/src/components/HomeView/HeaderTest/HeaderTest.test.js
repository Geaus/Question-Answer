import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import HeaderTest from './HeaderTest';

describe('HeaderTest', () => {
    beforeEach(() => {
        window.matchMedia = jest.fn().mockImplementation((query) => ({
            matches: false,
            media: query,
            onchange: null,
            addListener: jest.fn(),
            removeListener: jest.fn(),
            addEventListener: jest.fn(),
            removeEventListener: jest.fn(),
            dispatchEvent: jest.fn(),
        }));
    });

    test('renders the component', () => {
        render(
            <Router>
                <HeaderTest />
            </Router>
        );

        // Check if the component renders without errors
        const component = screen.getByText('My App');
        expect(component).toBeInTheDocument();
    });

    test('handles search correctly', () => {
        const mockNavigate = jest.fn();
        const mockReload = jest.fn();
        const mockSessionStorage = {
            getItem: jest.fn().mockReturnValue('1'),
            removeItem: jest.fn(),
        };
        Object.defineProperty(window, 'sessionStorage', { value: mockSessionStorage });

        render(
            <Router>
                <HeaderTest />
            </Router>
        );

        // Simulate typing in the search input
        const searchInput = screen.getByPlaceholderText('搜索');
        fireEvent.change(searchInput, { target: { value: 'test' } });

        // Simulate pressing the enter key to trigger the search
        fireEvent.keyPress(searchInput, { key: 'Enter', code: 13, charCode: 13 });

        // Check if the navigate function is called with the correct arguments
        expect(mockNavigate).toHaveBeenCalledWith('/?title=test');
        expect(mockReload).toHaveBeenCalled();
    });

    test('handles logout correctly', () => {
        const mockNavigate = jest.fn();
        const mockRemoveItem = jest.fn();
        const mockSessionStorage = {
            getItem: jest.fn().mockReturnValue('1'),
            removeItem: mockRemoveItem,
        };
        Object.defineProperty(window, 'sessionStorage', { value: mockSessionStorage });

        render(
            <Router>
                <HeaderTest />
            </Router>
        );

        // Click on the logout button
        const logoutButton = screen.getByText('Log out');
        fireEvent.click(logoutButton);

        // Check if the sessionStorage.removeItem function is called
        expect(mockRemoveItem).toHaveBeenCalledTimes(2);
        expect(mockRemoveItem).toHaveBeenCalledWith('uid');
        expect(mockRemoveItem).toHaveBeenCalledWith('type');

        // Check if the navigate function is called with the correct argument
        expect(mockNavigate).toHaveBeenCalledWith('/login');
    });
});
