import React from "react";
import { useBackend } from "main/utils/useBackend";
import BasicLayout from "main/layouts/BasicLayout/BasicLayout";
import DiningCommonsTable from "main/components/DiningCommons/DiningCommonsTable";

export default function DiningCommonsIndexPage() {
    const {
        data: diningCommons,
        error: _error,
        status: _status,
    } = useBackend(
        // Stryker disable next-line all : don't test internal caching of React Query
        ["/api/diningcommons/all"],
        { method: "GET", url: "/api/diningcommons/all" },
        // Stryker disable next-line all : don't test default value of empty list
        []
    );

    return (
        <BasicLayout>
            <div className="pt-2">
                <h1>Dining Commons</h1>
                <DiningCommonsTable diningcommons={diningCommons} />
            </div>
        </BasicLayout>
    );
}
